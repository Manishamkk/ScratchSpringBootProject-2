pipeline {

    agent any

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/Manishamkk/ScratchSpringBootProject-2.git'
            }
        }

        stage('Build') {
            steps {
                echo 'Building Spring Boot application...'
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                echo 'Running test cases...'
                bat 'mvn test'
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