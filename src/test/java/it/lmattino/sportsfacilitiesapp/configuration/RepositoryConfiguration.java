/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.lmattino.sportsfacilitiesapp.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author Mattinò
 */
@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"it.lmattino.sportsfacilitiesapp.model"})
@EnableJpaRepositories(basePackages = {"it.lmattino.sportsfacilitiesapp.repository"})
@EnableTransactionManagement
public class RepositoryConfiguration {
    
}
