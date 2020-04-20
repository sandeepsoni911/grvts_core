package com.owners.gravitas.service.util;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.Message.Level;
import org.kie.api.io.KieResources;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.owners.gravitas.constants.Constants.GRAVITAS_CONFIG_DIR;

import java.io.File;

@Service
public class RuleRunnerUtil {
    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( RuleRunnerUtil.class );

    public void runRules( String[] rules, Object[] facts ) {
        KieServices kieServices = KieServices.Factory.get();
        KieResources kieResources = kieServices.getResources();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        KieRepository kieRepository = kieServices.getRepository();

        for ( String ruleFile : rules ) {
            String filePath = GRAVITAS_CONFIG_DIR + File.separator + ruleFile;
            File fl = new File( filePath );
            Resource resource = kieResources.newFileSystemResource( fl ).setResourceType( ResourceType.DRL );
            LOGGER.info( GRAVITAS_CONFIG_DIR + File.separator + ruleFile );
            kieFileSystem.write( resource );
        }

        KieBuilder kb = kieServices.newKieBuilder( kieFileSystem );

        kb.buildAll();

        if (kb.getResults().hasMessages( Level.ERROR )) {
            throw new RuntimeException( "Build Errors:\n" + kb.getResults().toString() );
        }

        KieContainer kContainer = kieServices.newKieContainer( kieRepository.getDefaultReleaseId() );

        KieSession kSession = kContainer.newKieSession();

        for ( Object fact : facts ) {
            kSession.insert( fact );
        }

        kSession.fireAllRules();
    }
}
