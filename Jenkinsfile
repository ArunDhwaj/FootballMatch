//DECLARATIVE
pipeline {
    agent any
    //agent { myDockerHub { image 'maven:3.6.3'}}
    environment{
       dockerHome = tool 'myDocker'
       mavenHome = tool 'myMaven'
       PATH = "$dockerHome/bin:$mavenHome/bin:$PATH"
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn --version'
                sh 'docker version'
                echo "Build"
                echo "PATH - $PATH"
                echo "Build_NUMBER - $env.BUILD_NUMBER"
                echo "Build_ID - $env.BUILD_ID"
                echo "JOB_NAME - $env.JOB_NAME"
                echo "BUILD_TAG - $env.BUILD_TAG"
                echo "BUILD_URL - $env.BUILD_URL"
            }
        }
        stage('Compile') {
            steps {
                sh "mvn clean compile"
            }
        }
        stage('Test') {
            steps {
                echo "Test"
            }
        }
        stage('Integration Test') {
            steps {
                echo "Integration Test"
            }
        }
        stage('Build Docker Image') {
            steps {
                //docker build -t arundhwaj/FootballMatch:Prod-v1
                script {
                    dockerImage = docker.build("arundhwaj/FootballMatch:${env.BUILD_TAG}")
                }
            }
        }
        stage('Push Docker Image') {
            steps {
                script {
                    dockerImage.push('latest')
                }
            }
        }
    }

    post {
        always {
            echo 'I run always'
        }
        success {
            echo 'I run when i m suceessful'
        }
        failure {
            echo 'I run when i m failure'
        }
    }
}
