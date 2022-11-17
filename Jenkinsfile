pipeline{
    agent any

    stages{
        stage('Build'){
            steps{
                echo 'Building..'
                mvn clean package -DskipTests
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
                java -jar ./target/*.jar
            }
        }

    }
}