package com.syemon.repositorystatistics.domain;

public record ProjectStatisticsCommand (
        String ownerName,
        String repositoryName
) {

}
