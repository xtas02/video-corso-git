package org.marco.quartz.config;

import org.marco.quartz.jobs.LoggingJob; // Assicurati che il package sia corretto
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // Dice a Spring che questa classe contiene bean di configurazione
public class AlixConfig {

    @Bean // Questo metodo definisce il "dettaglio" del tuo job
    public JobDetail loggingJobDetail() {
        return JobBuilder.newJob(LoggingJob.class) // Associa questo dettaglio alla classe del tuo job
                .withIdentity("loggingJob", "default") // Nome e gruppo del job
                .storeDurably() // Fa sì che il job persista anche senza trigger attivi
                .build();
    }

    @Bean // Questo metodo definisce il "trigger" che farà scattare il job
    public Trigger loggingJobTrigger(JobDetail loggingJobDetail) {
        // L'espressione cron che avevi nel Liquibase: "0 0/1 * * * ?"
        // Significa: al secondo 0, ogni 1 minuto, ogni ora, ogni giorno del mese, ogni mese, ogni giorno della settimana
        String cronExpression = "0 0/1 * ? * *";

        return TriggerBuilder.newTrigger()
                .forJob(loggingJobDetail) // Associa questo trigger al JobDetail definito sopra
                .withIdentity("loggingTrigger", "default") // Nome e gruppo del trigger
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression) // Usa CronScheduleBuilder
                        .withMisfireHandlingInstructionFireAndProceed()) // Gestione dei misfire
                .build();
    }
}
