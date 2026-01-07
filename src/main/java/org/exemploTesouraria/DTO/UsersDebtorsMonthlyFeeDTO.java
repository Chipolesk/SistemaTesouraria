package org.exemploTesouraria.DTO;


import org.exemploTesouraria.model.enums.MonthEnum;

import java.util.List;

public record UsersDebtorsMonthlyFeeDTO(
        String userName,
        List<MonthEnum> openMonths
){}

