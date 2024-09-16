package com.org.seerbitbanking.model;

import java.math.BigDecimal;

public class Stats {

    private BigDecimal sum = BigDecimal.ZERO;
    private BigDecimal avg = BigDecimal.ZERO;
    private BigDecimal max = BigDecimal.ZERO;
    private BigDecimal min = BigDecimal.ZERO;
    private long count = 0;

    public void updateStats(BigDecimal amount) {
        sum = sum.add(amount);
        count++;
        avg = sum.divide(BigDecimal.valueOf(count), 2, BigDecimal.ROUND_HALF_UP);
        if (count == 1) {
            max = amount;
            min = amount;
        } else {
            max = max.max(amount);
            min = min.min(amount);
        }
    }

    public BigDecimal getSum() {
        return sum;
    }

    public BigDecimal getAvg() {
        return avg;
    }

    public BigDecimal getMax() {
        return max;
    }

    public BigDecimal getMin() {
        return min;
    }

    public long getCount() {
        return count;
    }
}
