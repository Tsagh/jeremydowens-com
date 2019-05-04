{
    createUser = () => new Promise((resolve, reject) => {
        let email = $('#create_acct_email').val();
        let uname = $('#create_acct_uname').val();

        if (email && uname) $.ajax({
            type: 'POST',
            url: "https://www.jeremydowens.com/createaccount",
            data: JSON.stringify({
                email,
                uname
            }),
            contentType: 'application/json',
            success: s => {
                if (s.success)
                    $('#resultmessage').text(s.success);
                resolve(true);
            },
            error: e => {
                reject(false);
            },
        });
    });

    $(document).ready(function() {
        $('#acctcreate').on('click', function() {
            createUser();
        });
    });
}