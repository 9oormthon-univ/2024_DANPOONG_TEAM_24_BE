package com.ieum.be.global.dto;

import java.time.LocalDateTime;

public class TimeDto {
    private final LocalDateTime time;

    public TimeDto(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        LocalDateTime now = LocalDateTime.now();
        long diffSeconds = java.time.Duration.between(time, now).getSeconds();
        
        final int SECONDS_IN_MINUTE = 60;
        final int SECONDS_IN_HOUR = SECONDS_IN_MINUTE * 60;      // 3,600
        final int SECONDS_IN_DAY = SECONDS_IN_HOUR * 24;         // 86,400
        final int SECONDS_IN_MONTH = SECONDS_IN_DAY * 30;        // 2,592,000
        final int SECONDS_IN_YEAR = SECONDS_IN_DAY * 365;        // 31,536,000
        
        if (diffSeconds < SECONDS_IN_MINUTE) {  // 1분 이내
            return diffSeconds + "초 전";
        } else if (diffSeconds < SECONDS_IN_HOUR) {  // 1시간 이내
            return (diffSeconds / SECONDS_IN_MINUTE) + "분 전";
        } else if (diffSeconds < SECONDS_IN_DAY) {  // 24시간 이내
            return (diffSeconds / SECONDS_IN_HOUR) + "시간 전";
        } else if (diffSeconds < SECONDS_IN_MONTH) {  // 30일 이내
            return (diffSeconds / SECONDS_IN_DAY) + "일 전";
        } else if (diffSeconds < SECONDS_IN_YEAR) {  // 1년 이내
            return (diffSeconds / SECONDS_IN_MONTH) + "달 전";
        } else {  // 1년 이후
            return (diffSeconds / SECONDS_IN_YEAR) + "년 전";
        }
    }
}
