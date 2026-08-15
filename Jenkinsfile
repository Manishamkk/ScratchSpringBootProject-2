pipeline {

    agent any

    tools {
        maven 'Maven-3.9.16'
    }
    
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
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                echo 'Running test cases...'
                sh 'mvn test'
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
