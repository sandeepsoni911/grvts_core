package com.owners.gravitas.service;

/**
 * The Interface RuleRunnerService.
 */
/**
 * @author kushwaja
 *
 */
public interface RuleRunnerService {

    /**
     * execute Rule Runner
     * 
     * @param facts
     * @param rules
     * @return
     */
    void executeRuleRunner( String[] rules, Object[] facts  );
}
