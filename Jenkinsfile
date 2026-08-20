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
                git branch: 'main',
                    url: 'https://github.com/Manishamkk/ScratchSpringBootProject-2.git'
            }
        }

        stage('Set Version') {
            steps {
                script {
                    env.VERSION = "v1.0.${env.BUILD_NUMBER}"
                    echo "Application Version: ${env.VERSION}"
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
                echo "Building Docker image with version: ${env.VERSION}"

                sh """
                    docker build \
                    -t ${APP_NAME}:${VERSION} \
                    -t ${APP_NAME}:latest \
                    .
                """

                echo "Docker images created:"
                echo "${APP_NAME}:${VERSION}"
                echo "${APP_NAME}:latest"
            }
        }

        stage('Docker Run') {
            steps {
                sh """
                    docker stop ${APP_NAME} || true
                    docker rm ${APP_NAME} || true

                    docker run -d \
                    -p ${DOCKER_PORT}:${DOCKER_PORT} \
                    --name ${APP_NAME} \
                    ${APP_NAME}:${VERSION}
                """

                echo "Docker container started using image: ${APP_NAME}:${VERSION}"
            }
        }

        stage('Git Tag') {
            steps {
                script {

                    withCredentials([usernamePassword(
                        credentialsId: 'github-credentials',
                        usernameVariable: 'GIT_USERNAME',
                        passwordVariable: 'GIT_TOKEN'
                    )]) {

                        sh """
                            git config user.name "Jenkins"
                            git config user.email "jenkins@example.com"

                            git tag -a ${VERSION} -m "Release ${VERSION}"

                            git push https://${GIT_USERNAME}:${GIT_TOKEN}@github.com/Manishamkk/ScratchSpringBootProject-2.git ${VERSION}
                        """
                    }

                    echo "Git Release Tag Created: ${VERSION}"
                }
            }
        }
    }

    post {

        success {
            echo 'Build, Test, SonarQube, Docker deployment and Git tagging successful!'
            echo "Release Version: ${env.VERSION}"
        }

        failure {
            echo 'Build, Docker deployment or Git tagging failed!'
        }
    }
}