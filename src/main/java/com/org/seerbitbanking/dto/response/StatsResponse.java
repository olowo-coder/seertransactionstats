package com.org.seerbitbanking.dto.response;

import com.org.seerbitbanking.model.Stats;

import java.math.BigDecimal;

public class StatsResponse {
    private String sum;
    private String avg;
    private String max;
    private String min;
    private long count;

    public StatsResponse(Stats stats) {
        this.sum = stats.getSum().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        this.avg = stats.getAvg().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        this.max = stats.getMax().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        this.min = stats.getMin().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        this.count = stats.getCount();
    }
}
