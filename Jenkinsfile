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
                    sh '''
                        mvn clean verify sonar:sonar \
                        -Dsonar.host.url=http://host.docker.internal:9000
                    '''
                }
            }
        }

        stage('Docker Build') {
            steps {
                echo 'Building Docker image...'

                sh '''
                    docker build -t scratchspringbootproject-2-app:latest .
                '''

                echo 'Docker Build stage successful!'
            }
        }

        stage('Docker Run') {
            steps {
                sh '''
                    docker stop scratchspringbootproject-2-app || true
                    docker rm scratchspringbootproject-2-app || true

                    docker run -d \
                    -p 8084:8084 \
                    --name scratchspringbootproject-2-app \
                    scratchspringbootproject-2-app:latest
                '''
            }
        }

        stage('Git Tag') {
            steps {
                script {

                    def tagName = "v1.0.${env.BUILD_NUMBER}"

                    withCredentials([usernamePassword(
                        credentialsId: 'github-credentials',
                        usernameVariable: 'GIT_USERNAME',
                        passwordVariable: 'GIT_TOKEN'
                    )]) {

                        sh """
                            git config user.name "Jenkins"
                            git config user.email "jenkins@example.com"

                            git tag -a ${tagName} -m "Release ${tagName}"

                            git push https://${GIT_USERNAME}:${GIT_TOKEN}@github.com/Manishamkk/ScratchSpringBootProject-2.git ${tagName}
                        """
                    }

                    echo "Git Release Tag Created: ${tagName}"
                }
            }
        }
    }

    post {

        success {
            echo 'Build, Docker deployment and Git tagging successful!'
        }

        failure {
            echo 'Build, Docker deployment or Git tagging failed!'
        }
    }
}