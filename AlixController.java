package org.marco.quartz.utils;

import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;
import org.springframework.stereotype.Component;
import java.util.Locale; // Per la lingua italiana

@Component
public class AlixController {

    public static String humanReadableCron(String cronExpression) {
        // Definisci il tipo di Cron (Quartz è molto comune)
        // Se la tua espressione Quartz include i secondi (come "0 0/1 * ? * *"), usa CronType.QUARTZ
        // Se non include i secondi (come "* 0/1 * * *"), usa CronType.UNIX
        CronParser parser = new CronParser(CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ));

        Cron cron = parser.parse(cronExpression);

        // Crea un descrittore per la lingua italiana
        CronDescriptor descriptor = CronDescriptor.instance(Locale.ITALIAN);

        // Ottieni la descrizione leggibile
        return descriptor.describe(cron);
    }

    public static void main(String[] args) {
        String cron = "0 0/1 * ? * *"; // Ogni minuto
        System.out.println("Cron: " + cron + " -> " + humanReadableCron(cron));

        String edlp = "0/10 * * ? * *"; //ogni 10 secondi
        System.out.println("Cron: " + edlp + " -> " + humanReadableCron(edlp));

        String dailyCron = "0 0 9 ? * MON-FRI"; // Alle 9:00, dal lunedì al venerdì
        System.out.println("Cron: " + dailyCron + " -> " + humanReadableCron(dailyCron));

        String monthlyCron = "0 0 10 1 * ?"; // Alle 10:00, il primo giorno del mese
        System.out.println("Cron: " + monthlyCron + " -> " + humanReadableCron(monthlyCron));
    }
}
