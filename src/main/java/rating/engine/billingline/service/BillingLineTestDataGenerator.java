package rating.engine.billingline.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import rating.engine.billingline.dto.BillingLineDto;
import rating.engine.billingline.persistence.BillingLineRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static rating.engine.billingline.persistence.BillingLineStatus.UNPROCESSED;

@Slf4j
@Service
@RequiredArgsConstructor
public class BillingLineTestDataGenerator {

    private final BillingLineRepository billingLineRepository;

    @Async
    public void generateBillingLine() {
        int batchSize = 5000;
        int totalBatches = 100;
        for (long i = 0; i < totalBatches; i++) {
            List<BillingLineDto> billingLineEntities = new ArrayList<>();
            for (long j = 0; j < batchSize; j++) {
                Long uniqueId = i * batchSize + j;
                BillingLineDto billingLineDto = createBillingLine(uniqueId);
                billingLineEntities.add(billingLineDto);
            }
            billingLineRepository.saveAll(billingLineEntities);
        }
    }

    private BillingLineDto createBillingLine(Long id) {
        return BillingLineDto.builder()
                .id(id)
                .contractId("CONTRACT_" + id)
                .startDate(Instant.now())
                .endDate(Instant.now())
                .productId("PRODUCT_1")
                .consumption(BigDecimal.valueOf(12))
                .status(UNPROCESSED)
                .build();
    }


}
