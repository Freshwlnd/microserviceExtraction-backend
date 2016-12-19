import ch.uzh.ifi.seal.monolith2microservices.graph.LinearGraphCombination;
import ch.uzh.ifi.seal.monolith2microservices.graph.MSTGraphClusterer;
import ch.uzh.ifi.seal.monolith2microservices.models.couplings.BaseCoupling;
import ch.uzh.ifi.seal.monolith2microservices.models.couplings.ContributorCoupling;
import ch.uzh.ifi.seal.monolith2microservices.models.couplings.LogicalCoupling;
import ch.uzh.ifi.seal.monolith2microservices.models.couplings.SemanticCoupling;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.Component;
import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.DecompositionService;
import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.logicalcoupling.LogicalCouplingDecompositor;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gmazlami on 12/15/16.
 */
public class CouplingTest {


    private List<LogicalCoupling> logicalCouplings;

    private List<ContributorCoupling> contributorCouplings;

    private List<SemanticCoupling> semanticCouplings;

    private List<BaseCoupling> expectedCouplings, expectedCouplings2;

    @Before
    public void setUp(){
        logicalCouplings = new ArrayList<>();
        logicalCouplings.add(new LogicalCoupling("A","B",5.0));
        logicalCouplings.add(new LogicalCoupling("A","C",3.0));
        logicalCouplings.add(new LogicalCoupling("C","D",2.0));
        logicalCouplings.add(new LogicalCoupling("B","D",7.0));

        contributorCouplings = new ArrayList<>();
        contributorCouplings.add(new ContributorCoupling("A","B", 2.0));
        contributorCouplings.add(new ContributorCoupling("A","C", 4.0));
        contributorCouplings.add(new ContributorCoupling("A","E", 3.0));

        semanticCouplings = new ArrayList<>();
        semanticCouplings.add(new SemanticCoupling("A","B",6.0));
        semanticCouplings.add(new SemanticCoupling("B","C",1.0));
        semanticCouplings.add(new SemanticCoupling("C","D",3.0));
        semanticCouplings.add(new SemanticCoupling("D","E",7.0));


        expectedCouplings = new ArrayList<>();
        expectedCouplings.add(new BaseCoupling("A","B", 13d));
        expectedCouplings.add(new BaseCoupling("B","C", 1d));
        expectedCouplings.add(new BaseCoupling("A","C", 7d));
        expectedCouplings.add(new BaseCoupling("C","D", 5d));
        expectedCouplings.add(new BaseCoupling("B","D", 7d));
        expectedCouplings.add(new BaseCoupling("A","E", 3d));
        expectedCouplings.add(new BaseCoupling("D","E", 7d));

        expectedCouplings2 = new ArrayList<>();
        expectedCouplings2.add(new BaseCoupling("A","B",8d));
        expectedCouplings2.add(new BaseCoupling("B","C",1d));
        expectedCouplings2.add(new BaseCoupling("A","C",4d));
        expectedCouplings2.add(new BaseCoupling("C","D",3d));
        expectedCouplings2.add(new BaseCoupling("A","E",3d));
        expectedCouplings2.add(new BaseCoupling("D","E",7d));


    }



    @Test
    public void testCombinedCouplings(){
        List<BaseCoupling> combinedCouplings = LinearGraphCombination.create().withContributorCouplings(contributorCouplings).withLogicalCouplings(logicalCouplings)
                .withSemanticCouplings(semanticCouplings).generate();
        assertEquals(expectedCouplings,combinedCouplings);
    }

    @Test
    public void testCombinedCouplingsWithoutLogical(){
        List<BaseCoupling> combinedCouplings = LinearGraphCombination.create().withContributorCouplings(contributorCouplings).withSemanticCouplings(semanticCouplings).generate();
        assertEquals(expectedCouplings2,combinedCouplings);
    }

    @Test
    public void testLogicalCouplingCombination(){
        List<LogicalCoupling> couplings = new ArrayList<>();
        couplings.add(new LogicalCoupling("A","B",5.0));
        couplings.add(new LogicalCoupling("A","C",3.0));
        couplings.add(new LogicalCoupling("C","D",2.0));
        couplings.add(new LogicalCoupling("B","D",7.0));
        couplings.add(new LogicalCoupling("C","E",1.0));
        couplings.add(new LogicalCoupling("E","H",7.0));
        couplings.add(new LogicalCoupling("H","I",8.0));
        couplings.add(new LogicalCoupling("D","G",2.0));
        couplings.add(new LogicalCoupling("G","L",8.0));
        couplings.add(new LogicalCoupling("L","M",7.0));
        couplings.add(new LogicalCoupling("M","G",6.0));
        couplings.add(new LogicalCoupling("B","F",2.0));
        couplings.add(new LogicalCoupling("F","J",9.0));
        couplings.add(new LogicalCoupling("J","K",7.0));
        couplings.add(new LogicalCoupling("K","F",5.0));

        List<BaseCoupling> combinedCouplings = LinearGraphCombination.create().withLogicalCouplings(couplings).generate();
        List<Component> componentsFromCombined = MSTGraphClusterer.clusterFromCouplings(combinedCouplings);

        List<Component> componentsFromLogicalCoupling = MSTGraphClusterer.clusterFromCouplings(couplings);

        assertEquals(componentsFromCombined, componentsFromLogicalCoupling);

    }
}
