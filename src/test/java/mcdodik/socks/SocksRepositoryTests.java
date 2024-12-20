package mcdodik.socks;

import mcdodik.socks.domain.model.entity.Sock;
import mcdodik.socks.domain.model.view.SockViewFilter;
import mcdodik.socks.domain.repository.SockRepository;
import mcdodik.socks.domain.model.specifications.SockSpecification;
import mcdodik.socks.infrastructure.enums.FilterOperations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class SocksRepositoryTests {

    @Autowired
    private SockRepository sockRepository;

    private Sock sock1;
    private Sock sock2;

    @BeforeEach
    void setUp() {
        sockRepository.deleteAll();

        sock1 = new Sock(null, "Красный", 40, 70);
        sock2 = new Sock(null, "Синий", 42, 80);

        sockRepository.save(sock1);
        sockRepository.save(sock2);
    }

    @Test
    void testFindAllWithoutSpecification() {
        SockViewFilter filter = new SockViewFilter();
        Specification<Sock> spec = new SockSpecification().getConfiguredSpecification(filter);

        List<Sock> result = sockRepository.findAll(spec);

        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(sock1, sock2);
    }

    @Test
    void testFindAllWithColorFilter() {
        String testColor = "Красный";
        SockViewFilter filter = new SockViewFilter();
        filter.setColor(testColor);
        Specification<Sock> spec = new SockSpecification().getConfiguredSpecification(filter);

        List<Sock> result = sockRepository.findAll(spec);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getColor()).isEqualTo(testColor);
    }

    @Test
    void testFindAllWithCottonPercentageEqual() {
        Integer testPercentage = 40;
        SockViewFilter filter = new SockViewFilter();
        filter.setCottonPercentage(testPercentage);
        filter.setCottonPercentageOperations(FilterOperations.EQUAL);
        Specification<Sock> spec = new SockSpecification().getConfiguredSpecification(filter);

        List<Sock> result = sockRepository.findAll(spec);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCottonPercentage()).isEqualTo(testPercentage);
    }


    @Test
    void testFindZeroWithCottonPercentageEqual() {
        Integer testPercentage = 50;
        SockViewFilter filter = new SockViewFilter();
        filter.setCottonPercentage(testPercentage);
        filter.setCottonPercentageOperations(FilterOperations.EQUAL);
        Specification<Sock> spec = new SockSpecification().getConfiguredSpecification(filter);

        List<Sock> result = sockRepository.findAll(spec);

        assertThat(result).hasSize(0);
    }

    @Test
    void testFindAllWithCottonPercentageMore() {
        Integer testPercentage = 10;
        SockViewFilter filter = new SockViewFilter();
        filter.setCottonPercentage(testPercentage);
        filter.setCottonPercentageOperations(FilterOperations.MORE_THEN);
        Specification<Sock> spec = new SockSpecification().getConfiguredSpecification(filter);

        List<Sock> result = sockRepository.findAll(spec);

        assertThat(result).hasSize(2);
    }

    @Test
    void testFindAllWithCottonRange() {
        Integer testMin = 30;
        Integer testMax = 41;
        SockViewFilter filter = new SockViewFilter();
        filter.setCottonPercentRange("от " + testMin + " до " + testMax);
        Specification<Sock> spec = new SockSpecification().getConfiguredSpecification(filter);

        List<Sock> result = sockRepository.findAll(spec);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCottonPercentage()).isBetween(testMin, testMax);
    }

    @Test
    void testFindAllWithCombineFilter() {
        Integer testMin = 30;
        Integer testMax = 41;
        String testColor = "Красный";
        SockViewFilter filter = new SockViewFilter();
        filter.setColor(testColor);
        filter.setCottonPercentRange("от " + testMin + " до " + testMax);
        Specification<Sock> spec = new SockSpecification().getConfiguredSpecification(filter);

        List<Sock> result = sockRepository.findAll(spec);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getColor()).isEqualTo(testColor);
        assertThat(result.get(0).getCottonPercentage()).isBetween(testMin, testMax);
    }


}
