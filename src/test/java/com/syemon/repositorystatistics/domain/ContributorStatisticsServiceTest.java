package com.syemon.repositorystatistics.domain;

import com.syemon.repositorystatistics.domain.in.ProjectStatisticsCommandHandler;
import com.syemon.repositorystatistics.domain.out.ContributorStatisticsPort;
import com.syemon.repositorystatistics.domain.out.ProjectStatisticsPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContributorStatisticsServiceTest {

    public static final String OWNER_NAME = "assertj";
    public static final String REPOSITORY_NAME = "assertj";

    @InjectMocks
    private ContributorStatisticsService sut;

    @Mock
    private ProjectStatisticsPort projectStatisticsPort;
    @Mock
    private ContributorStatisticsPort contributorStatisticsPort;
    @Mock
    private ProjectStatisticsCommandHandler projectStatisticsCommandHandler;

    private ProjectStatistics projectStatistics;

    @BeforeEach
    public void setUp() {
        projectStatistics = new ProjectStatistics();
        projectStatistics.setName(REPOSITORY_NAME);
        projectStatistics.setOwnerName(OWNER_NAME);
    }

    @Test
    void saveProjectStatistics_shouldReturnError_whenReceivedEmptyProject() {
        //given
        ProjectStatisticsCommand projectStatisticsCommand = new ProjectStatisticsCommand(
                OWNER_NAME,
                REPOSITORY_NAME
        );

        when(projectStatisticsPort.getProjectStatistics(projectStatisticsCommand))
                .thenReturn(Mono.empty());

        //when
        Mono<ProjectStatistics> result = sut.saveProjectStatistics(projectStatisticsCommand);

        //then
        StepVerifier.create(result)
                .expectError(ProjectNotFoundException.class)
                .verify();

        verifyNoInteractions(projectStatisticsCommandHandler);
    }

    @Test
    void saveProjectStatistics_shouldReturnError_whenReceivedError() {
        //given
        ProjectStatisticsCommand projectStatisticsCommand = new ProjectStatisticsCommand(
                OWNER_NAME,
                REPOSITORY_NAME
        );
        ContributorStatisticsCommand contributorStatisticsCommand = new ContributorStatisticsCommand(
                OWNER_NAME,
                REPOSITORY_NAME
        );

        when(projectStatisticsPort.getProjectStatistics(projectStatisticsCommand))
                .thenReturn(Mono.just(projectStatistics));

        when(contributorStatisticsPort.getContributorStatistics(contributorStatisticsCommand))
                .thenReturn(Flux.error(new RuntimeException("Test exception")));

        //when
        Mono<ProjectStatistics> result = sut.saveProjectStatistics(projectStatisticsCommand);

        //then
        StepVerifier.create(result)
                .expectErrorSatisfies(error -> {
                    assertThat(error).isInstanceOf(RuntimeException.class);
                    assertThat(error.getMessage()).isEqualTo("Test exception");
                })
                .verify();
    }

    @Test
    void saveProjectStatistics() {
        //given
        ProjectStatisticsCommand projectStatisticsCommand = new ProjectStatisticsCommand(
                OWNER_NAME,
                REPOSITORY_NAME
        );
        ContributorStatisticsCommand contributorStatisticsCommand = new ContributorStatisticsCommand(
                OWNER_NAME,
                REPOSITORY_NAME
        );

        when(projectStatisticsPort.getProjectStatistics(projectStatisticsCommand))
                .thenReturn(Mono.just(projectStatistics));

        when(contributorStatisticsPort.getContributorStatistics(contributorStatisticsCommand))
                .thenReturn(Flux.just(new ContributorStatistics()));
        when(projectStatisticsCommandHandler.save(projectStatistics))
                .thenAnswer(answer -> Mono.just(answer.getArgument(0)));

        //when
        Mono<ProjectStatistics> result = sut.saveProjectStatistics(projectStatisticsCommand);

        //then
        StepVerifier.create(result)
                .expectNextMatches(savedProjectStatistics -> {
                    assertThat(savedProjectStatistics.getName()).isEqualTo(REPOSITORY_NAME);
                    assertThat(savedProjectStatistics.getContributors()).hasSize(1);
                    return true;
                })
                .verifyComplete();
    }

}