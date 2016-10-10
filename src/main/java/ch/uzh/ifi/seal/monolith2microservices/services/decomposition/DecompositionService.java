package ch.uzh.ifi.seal.monolith2microservices.services.decomposition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import ch.uzh.ifi.seal.monolith2microservices.models.GitRepository;

@Service
public class DecompositionService {

	private static final Logger logger = LoggerFactory.getLogger(DecompositionService.class);
	
	@Autowired
	@Qualifier("logicalCouplingDecompositor")
	Decompositor decompositor;
	
	@Async
	public void process(GitRepository repo){
		long startTime = System.currentTimeMillis();
		
		decompositor.decompose(repo);
		
		long endTime = System.currentTimeMillis();
		
		logger.info("Total execution time: " + String.valueOf(endTime - startTime) + " ms");
	}
	
}
