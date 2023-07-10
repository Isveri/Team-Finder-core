package com.evi.teamfindercore.mapper;

import com.evi.teamfindercore.domain.Report;
import com.evi.teamfindercore.model.ReportDTO;
import org.mapstruct.Builder;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(builder = @Builder(disableBuilder = true),
        uses = UserMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class ReportMapper {

    public abstract ReportDTO mapReportToReportDTO(Report report);

    public abstract Report mapReportDTOToReport(ReportDTO reportDTO);

}
