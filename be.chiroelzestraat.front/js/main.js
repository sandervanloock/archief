var subscribeJubileeTd = function(){
    FB.api(
        "/815590505148519",
        function (response) {
            if (response && !response.error) {
                console.log(response);
            }
        }
    );
}
