package org.example;

import org.eclipse.jgit.api.Git;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GitHubPRCreator {

    // For Testing Purpose used fixed values
    private static final String REPO_URI = "https://github.com/srid3/SetupRepoLocally.git";
    private static final String CLONE_DIRECTORY = "temp-repo";
    private static final String PAT = System.getenv("TOKEN");

    public static void main(String[] args) {
        try {
            // Clone the repo
            RepositoryCloner cloner = new RepositoryCloner(REPO_URI, CLONE_DIRECTORY);
            Git git = cloner.cloneRepository();
            // Generate a unique branch name
            String branchName = generateBranchName();

            // Commit changes to the branch
            CommitChanges.commitChanges(branchName, "Activating Recipes...", PAT);

            // Create a pull request for the committed changes
            CreatePullRequest.createPR(branchName, "Applying Recipes", "Activated the following Recipes...", PAT);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String generateBranchName() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return "rewrite-" + now.format(formatter);
    }
}
