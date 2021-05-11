package com.Data;

import java.io.Serializable;

public class Report implements Serializable {
    private ReportState reportState;
    private String reportBody;
    private ReportState answerState;

    public Report(ReportState reportState, String reportBody, ReportState answerState) {
        this.reportState = reportState;
        this.reportBody = reportBody;
        this.answerState = answerState;
    }

    public ReportState getReportState() {
        return reportState;
    }

    public String getReportBody() {
        return reportBody;
    }

    public ReportState getAnswerState() {
        return answerState;
    }
}