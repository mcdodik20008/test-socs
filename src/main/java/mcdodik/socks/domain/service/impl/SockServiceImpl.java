package mcdodik.socks.domain.service.impl;

import lombok.RequiredArgsConstructor;
import mcdodik.socks.domain.model.entity.Sock;
import mcdodik.socks.domain.model.mapper.SockMapper;
import mcdodik.socks.domain.model.view.*;
import mcdodik.socks.domain.repository.SockRepository;
import mcdodik.socks.domain.service.api.SockService;
import mcdodik.socks.domain.model.specifications.SockSpecification;
import mcdodik.socks.infrastructure.exceptions.FileProcessingException;
import mcdodik.socks.infrastructure.exceptions.InsufficientStockException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SockServiceImpl implements SockService {
    // ToDo можно конечно было добавить логгер через аспект, но я поленился
    // И можно конечно было написать, что вот, не удалось сохранить такой-то объект и тп
    private static final Logger logger = LoggerFactory.getLogger(SockServiceImpl.class);

    private final SockRepository repository;
    private final SockMapper mapper = SockMapper.INSTANCE;

    public void addIncome(Long id, SockViewIncome view) {
        Sock entity = getObjectThrow(id);

        entity.setQuantity(entity.getQuantity() + view.getQuantity());

        logger.info("Регистрация прихода носков на склад. Регистрируемый приход: {}", entity);
        repository.save(entity);
        logger.info("Регистрация прихода носков на склад прошла успешно. Зарегистрированный приход: {}", entity);
    }

    public void addOutcome(Long id, SockViewOutcome view) {
        Sock entity = getObjectThrow(id);
        if (entity.getQuantity() < view.getQuantity()) {
            throw new InsufficientStockException("Невозможно провести отпуск носков. Товара не хватает на складе для проведения отпуска.");
        }
        entity.setQuantity(entity.getQuantity() - view.getQuantity());

        logger.info("Регистрация отпуска носков со склада. Регистрируемый отпуск: {}", entity);
        repository.save(entity);
        logger.info("Регистрация отпуска носков со склад прошла успешно. Зарегистрированный отпуск: {}", entity);
    }

    public List<SockViewRead> findAll(SockViewFilter view) {
        List<Sock> entity = null;
        Specification<Sock> specification = new SockSpecification().getConfiguredSpecification(view);
        if (view.getSort() != null) {
            entity = repository.findAll(specification, view.getSort());
        } else {
            entity = repository.findAll(specification);
        }
        return entity.stream().map(mapper::toViewRead).toList();
    }

    public Sock update(Long id, SockViewUpdate view) {
        Sock entity = getObjectThrow(id);
        logger.info("Изменение данных о носке. Идентификатор носка: {}, Модифицируемые данные {}", id, entity);
        entity = mapper.formViewUpdate(entity, view);
        entity = repository.save(entity);
        logger.info("Изменение данных о носке прошло успешно. Идентификатор носка: {}, Модифицируемые данные {}", id, entity);
        return entity;
    }

    public Integer uploadBatch(MultipartFile file) {
        if (!"text/csv".equals(file.getContentType())) {
            throw new UnsupportedOperationException("Сервис может обработать только файлы с расширением CSV. Загрузите CSV файл.");
        }
        List<SockViewUpdate> view = parseCsv(file);
        List<Sock> entities = mapper.formViewUpdate(view);
        logger.info("Внесение списка носков на склад. Регистрируемый отпуск: {}", entities);
        repository.saveAll(entities);
        logger.info("Внесение списка носков на склад прошло успешно. Зарегистрированный отпуск: {}", entities);
        return entities.size();
    }

    private Sock getObjectThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new HttpServerErrorException(HttpStatus.NOT_FOUND, "На складе не ведется учет носков с идентификатором: " + id)
                );
    }

    // ToDo: Вынести в инфраструктуру, сделать Generic
    private List<SockViewUpdate> parseCsv(MultipartFile file) {
        List<SockViewUpdate> sockViewUpdates = new ArrayList<>();

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader("цвет", "процентное содержание хлопка", "количество")
                .setDelimiter(",")
                .setSkipHeaderRecord(true)
                .build();

        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            CSVParser csvParser = new CSVParser(reader, csvFormat);
            for (CSVRecord record : csvParser) {
                String color = record.get("цвет");
                String cottonPercentage = record.get("процентное содержание хлопка");
                String quantity = record.get("количество");

                SockViewUpdate sockViewUpdate = new SockViewUpdate();
                sockViewUpdate.setColor(color);
                sockViewUpdate.setCottonPercentage(Integer.parseInt(cottonPercentage));
                sockViewUpdate.setQuantity(Integer.parseInt(quantity));

                sockViewUpdates.add(sockViewUpdate);
            }
        } catch (Exception e) {
            throw new FileProcessingException("Невозможно обработать CSV файл");
        }

        return sockViewUpdates;
    }
}

