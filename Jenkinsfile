pipeline {

    agent any

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/YOUR_USERNAME/YOUR_REPOSITORY.git'
            }
        }

       
    }

    post {
        success {
            echo 'Build and deployment successful!'
        }

        failure {
            echo 'Build or deployment failed!'
        }
    }
}