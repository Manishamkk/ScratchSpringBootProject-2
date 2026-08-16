pipeline {

    agent any

    tools {
        maven 'maven'
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
        
 
stage('SonarQube Analysis') {
    steps {
        withSonarQubeEnv('SonarQube') {
            sh 'mvn clean verify sonar:sonar -Dsonar.host.url=http://host.docker.internal:9000'
        }
    }
}



stage('Docker Build') {
    steps {
        sh 'docker build -t ScratchSpringBootProject-2-app:latest .'
        echo ' Docker Build  stage successful!'
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
