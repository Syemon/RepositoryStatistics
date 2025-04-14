package com.syemon.repositorystatistics.domain;

public record ContributorStatisticsCommand(
        String ownerName,
        String repositoryName
) {

}
