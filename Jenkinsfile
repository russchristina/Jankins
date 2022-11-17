pipeline{
    agent any
    tools {
        maven 'apache-maven-3.0.5'
    }

    stages{
        stage('Build'){
            steps{
                echo 'Building..'
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test'){
            steps{
                echo 'Testing...'
            }
        }

        stage('Deploy'){
            steps{
                echo 'Deploying...'
                sh 'java -jar ./target/*.jar'
            }
        }

    }
}