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

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "StatsResponse{" +
            "sum='" + sum + '\'' +
            ", avg='" + avg + '\'' +
            ", max='" + max + '\'' +
            ", min='" + min + '\'' +
            ", count=" + count +
            '}';
    }
}
