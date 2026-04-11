def buildapp(){
    echo "building the app"
}

def testapp(){
    echo "testing the app"
}

def deployapp(){
    echo "deploying the app"
    echo "deploying the stage"
    echo "deploying version ${params.VERSION}"
}
return this