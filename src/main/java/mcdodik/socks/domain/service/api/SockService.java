package mcdodik.socks.domain.service.api;

import mcdodik.socks.domain.model.entity.Sock;
import mcdodik.socks.domain.model.view.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SockService {

    void addIncome(Long id, SockViewIncome view);

    void addOutcome(Long id, SockViewOutcome view);

    List<SockViewRead> findAll(SockViewFilter view);

    Sock update(Long id, SockViewUpdate view);

    Integer uploadBatch(MultipartFile file);
}
