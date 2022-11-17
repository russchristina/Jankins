pipeline{
    agent any

    stages{
        stage('Build'){
            steps{
                echo 'Building..'
                sh 'sudo ./mvnw clean package -DskipTests'
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