package org.example;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.transport.RefSpec;


import java.io.File;
import java.io.IOException;

public class CommitChanges {

    public static void commitChanges(String branchName, String commitMessage, String token) {
        try {
            // Open the repository
            Repository repo = Git.open(new File("temp-repo")).getRepository();
            Git git = new Git(repo);
            System.out.println("Repository Cloned to the local successfully.");

            // Create a new branch with the specified name
            git.branchCreate()
                    .setName(branchName)
                    .call();

            System.out.println("Branch Created Successfully.");

            // Checkout to the newly created branch
            git.checkout()
                    .setName(branchName)
                    .call();

            // Make changes to the files in the repository
            // For demonstration, let's create a new file
            File newFile = new File("temp-repo/newfile.txt");
            newFile.createNewFile();

            // Stage the file
            git.add().addFilepattern(".").call();

            // Commit the changes
            git.commit()
                    .setMessage(commitMessage)
                    .call();

            // Push changes to remote
            git.push()
                    .setRemote("origin")
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider("token", token))
                    .setRefSpecs(new RefSpec(branchName))
                    .call();

            System.out.println("Changes committed and pushed successfully!");

        } catch (IOException | GitAPIException e) {
            e.printStackTrace();
        }
    }
}
