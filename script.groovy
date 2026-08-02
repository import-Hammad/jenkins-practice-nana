
def buildApp(){
    echo "building the app"
}

def testApp(){
    echo "testing the app"
}
    
def deployApp(){
    echo "deploying the application"
    echo "deploying version ${params.VERSION}"
}
return this