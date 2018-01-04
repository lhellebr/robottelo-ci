node('sat6-rhel7') {

    snapperStage('Setup Git Repos') {

        deleteDir()
        gitlab_clone_and_merge(plugin_name)

    }

    snapperStage('Run Tests') {

        try {

            gitlabCommitStatus {
                withRVM(['bundle install'], 2.2)
                withRVM(['bundle exec rake ci:setup:minitest test TESTOPTS="-v"'], 2.2)
            }

        } finally {

            archive "Gemfile.lock"

            cleanup()

        }
    }

}
