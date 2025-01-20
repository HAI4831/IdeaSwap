package nvh.run.ideaswap.data.mapper;

import nvh.run.ideaswap.data.dto.ReportDTO;
import nvh.run.ideaswap.data.entity.Reports;

public class ReportMapper {
    public static ReportDTO toDto(Reports report) {
        if (report == null) {
            return null;
        }
        return ReportDTO.builder()
                .id(report.getId())
                .content(report.getContent())
                .referenceId(report.getReferenceID().toHexString())
                .userId(report.getUserID().getId())
                .type(report.getType())
                .status(report.getStatus())
                .moderatorId(report.getModeratorID().getId())
                .build();
    }

    public static Reports toEntity(ReportDTO dto) {
        if (dto == null) {
            return null;
        }
        Reports report = Reports.builder()
                .id(dto.getId())
                .content(dto.getContent())
                .type(dto.getType())
                .status(dto.getStatus())
                .build();
        return report;
    }
}

