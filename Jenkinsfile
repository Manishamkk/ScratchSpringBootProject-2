pipeline {

    agent any

    tools {
        maven 'maven'
    }

    environment {
        APP_NAME = 'scratchspringbootproject-2-app'
        DOCKER_PORT = '8084'
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Checking out source code...'

                git branch: 'main',
                    url: 'https://github.com/Manishamkk/ScratchSpringBootProject-2.git'
            }
        }

        stage('Set Version') {
            steps {
                script {
                    env.VERSION = "v1.0.${env.BUILD_NUMBER}"

                    echo "======================================"
                    echo "Application Version: ${env.VERSION}"
                    echo "======================================"
                }
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
                echo 'Running JUnit test cases...'

                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {

                echo 'Running SonarQube analysis...'

                withSonarQubeEnv('SonarQube') {

                    sh '''
                        mvn verify sonar:sonar \
                        -Dsonar.host.url=http://host.docker.internal:9000
                    '''
                }
            }
        }

        stage('Git Tag') {
            steps {

                script {

                    echo "Creating Git tag: ${VERSION}"

                    withCredentials([
                        usernamePassword(
                            credentialsId: 'github-credentials',
                            usernameVariable: 'GIT_USERNAME',
                            passwordVariable: 'GIT_TOKEN'
                        )
                    ]) {

                        sh '''
                            git config user.name "Jenkins"
                            git config user.email "jenkins@example.com"

                            echo "Creating tag ${VERSION}"

                            git tag -a "${VERSION}" \
                                -m "Release ${VERSION}"

                            echo "Pushing tag ${VERSION} to GitHub"

                            git push \
                                https://${GIT_USERNAME}:${GIT_TOKEN}@github.com/Manishamkk/ScratchSpringBootProject-2.git \
                                "${VERSION}"

                            echo "Git tag ${VERSION} pushed successfully!"
                        '''
                    }

                    echo "GitHub Release Tag Created: ${VERSION}"
                }
            }
        }

        stage('Docker Build') {
            steps {

                echo "Building Docker image..."
                echo "Version: ${VERSION}"

                sh '''
                    docker build \
                    -t ${APP_NAME}:${VERSION} \
                    -t ${APP_NAME}:latest \
                    .
                '''

                echo "Docker images created successfully."

                sh '''
                    docker images ${APP_NAME}
                '''
            }
        }

        stage('Docker Run') {
            steps {

                echo "Stopping old container if it exists..."

                sh '''
                    docker stop ${APP_NAME} || true
                    docker rm ${APP_NAME} || true
                '''

                echo "Starting Docker container..."

                sh '''
                    docker run -d \
                    -p ${DOCKER_PORT}:${DOCKER_PORT} \
                    --name ${APP_NAME} \
                    ${APP_NAME}:${VERSION}
                '''

                echo "Docker container started."
                echo "Container Name: ${APP_NAME}"
                echo "Docker Image: ${APP_NAME}:${VERSION}"
                echo "Application Port: ${DOCKER_PORT}"

                sh '''
                    docker ps
                '''
            }
        }
    }

    post {

        success {

            echo "=========================================="
            echo "PIPELINE SUCCESS"
            echo "=========================================="

            echo "Application Version : ${env.VERSION}"
            echo "Docker Image        : ${env.APP_NAME}:${env.VERSION}"
            echo "Docker Latest       : ${env.APP_NAME}:latest"
            echo "GitHub Tag          : ${env.VERSION}"

            echo "=========================================="
        }

        failure {

            echo "=========================================="
            echo "PIPELINE FAILED"
            echo "=========================================="

            echo "Please check the Jenkins console log."

            echo "=========================================="
        }
    }
}
