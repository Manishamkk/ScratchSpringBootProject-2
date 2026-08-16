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
        sh  'docker build -t  scratchspringbootproject-2-app:latest .'
        echo ' Docker Build  stage successful!'
    }
}

stage('Docker Run') {
    steps {
        sh '''
            docker stop scratchspringbootproject-2-app || true
            docker rm scratchspringbootproject-2-app || true

            docker run -d 
                -p 8084:8084 
                --name scratchspringbootproject-2-app 
                scratchspringbootproject-2-app:latest
        '''
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
