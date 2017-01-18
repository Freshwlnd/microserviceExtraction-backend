package ch.uzh.ifi.seal.monolith2microservices.models.graph;

import ch.uzh.ifi.seal.monolith2microservices.models.DecompositionParameters;
import ch.uzh.ifi.seal.monolith2microservices.models.git.GitRepository;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by gmazlami on 1/12/17.
 */

@Entity
public class Decomposition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade={CascadeType.REMOVE})
    private Set<Component> services;

    @OneToOne(cascade={CascadeType.REMOVE})
    private GitRepository repository;

    @OneToOne(cascade={CascadeType.REMOVE})
    private DecompositionParameters parameters;

    public Long getId(){
        return this.id;
    }

    public void setParameters(DecompositionParameters params){
        this.parameters = params;
    }

    public DecompositionParameters getParameters(){
        return this.parameters;
    }

    public void setComponents(Set<Component> services){
        this.services = services;
    }

    public Set<Component> getServices() {
        return this.services;
    }

    public GitRepository getRepository(){
        return this.repository;
    }

    public void setRepository(GitRepository repo){
        this.repository = repo;
    }

}
