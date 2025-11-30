package rating.engine.billingline.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import rating.engine.billingline.dto.BillingLineDto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import static java.util.stream.Collectors.joining;

@Repository
@RequiredArgsConstructor
public class BillingLineRepository {

    private final JdbcClient jdbcClient;
    private final JdbcTemplate jdbcTemplate;

    public List<BillingLineDto> findByStatusWithLimit(BillingLineStatus status, int limit) {
        return jdbcClient.sql("""
                        SELECT id, contract_id, start_date, end_date, product_id, consumption, status
                        FROM rating_engine.billing_line
                        WHERE status = :status
                        LIMIT :limit
                        """)
                .param("status", status.name())
                .param("limit", limit)
                .query((rs, rowNum) -> BillingLineDto.builder()
                        .id(rs.getLong("id"))
                        .contractId(rs.getString("contract_id"))
                        .startDate(rs.getTimestamp("start_date").toInstant())
                        .endDate(rs.getTimestamp("end_date").toInstant())
                        .productId(rs.getString("product_id"))
                        .consumption(rs.getBigDecimal("consumption"))
                        .status(BillingLineStatus.valueOf(rs.getString("status")))
                        .build())
                .list();
    }

    public void updateStatusByIds(BillingLineStatus status, List<Long> ids) {
        if (ids.isEmpty()) return;

        String idList = ids.stream().map(String::valueOf).collect(joining(", "));

        jdbcTemplate.update(
                "UPDATE rating_engine.billing_line SET status = '" + status + "' WHERE id IN (" + idList + ")"
        );
    }

    public void saveAll(List<BillingLineEntity> entities) {
        if (entities.isEmpty()) {
            return;
        }

        StringJoiner valuesJoiner = new StringJoiner(", ");
        List<Object> params = new ArrayList<>(entities.size() * 7);

        for (BillingLineEntity entity : entities) {
            valuesJoiner.add("(?, ?, ?, ?, ?, ?, ?)");
            params.add(entity.getId());
            params.add(entity.getContractId());
            params.add(Timestamp.from(entity.getStartDate()));
            params.add(Timestamp.from(entity.getEndDate()));
            params.add(entity.getProductId());
            params.add(entity.getConsumption());
            params.add(entity.getStatus().name());
        }

        String sql = """
                INSERT INTO rating_engine.billing_line 
                (id, contract_id, start_date, end_date, product_id, consumption, status)
                VALUES 
                """ + valuesJoiner;

        jdbcTemplate.update(sql, params.toArray());
    }

}