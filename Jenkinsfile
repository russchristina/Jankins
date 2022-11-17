pipeline{
    agent any

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