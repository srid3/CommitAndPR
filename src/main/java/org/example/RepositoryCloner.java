package org.example;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;

public class RepositoryCloner {

    private final String repoUri;
    private final String directoryPath;

    public RepositoryCloner(String repoUri, String directoryPath) {
        this.repoUri = repoUri;
        this.directoryPath = directoryPath;
    }

    public Git cloneRepository() throws GitAPIException {
        return Git.cloneRepository()
                .setURI(repoUri)
                .setDirectory(new File(directoryPath))
                .call();
    }
}
