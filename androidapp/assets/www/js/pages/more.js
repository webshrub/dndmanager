createSpinner("res/lib/jquerymobile/images/ajax-loader.gif");

$("#more").die("pageinit").live("pageinit", function (event, ui) {
//    FB.init({
//        appId:'117635671730897',
//        nativeInterface:CDV.FB,
//        useCachedDialogs:false
//    });
//    $('#j_14 [name="mobilelistitem1_10"]').die().live({
//        click:function () {
//            if (!$(this).attr('disabled')) {
//                publishStory();
//            }
//        }
//    });
//    $('#j_14 [name="mobilelistitem1_11"]').die().live({
//        click:function () {
//            if (!$(this).attr('disabled')) {
////                publishPost();
//                postToWall();
//            }
//        }
//    });
});

function publishStory() {
    FB.ui({
        method:'feed',
        link:'http://apps.facebook.com/mobile-start/',
        picture:'http://www.facebookmobileweb.com/hackbook/img/facebook_icon_large.png',
        name:'I\'m using the Hackbook web app',
        caption:'Hackbook for Mobile Web.',
        description:'Check out Hackbook for Mobile Web to learn how you can make your web apps social using Facebook Platform.',
        actions:{ name:'Get Started', link:'http://apps.facebook.com/mobile-start/' }

    }, function (response) {
        console.log('publishStory UI response: ', JSON.parse(response));
    });
}

function postToWall() {
    FB.login(function (response) {
        if (response.authResponse) {
            var publish = {
                method:'stream.publish',
                link:'http://www.takwing.idv.hk/facebook/demoapp_jssdk/',
                picture:'http://www.takwing.idv.hk/facebook/demoapp_jssdk/img/logo.gif',
                name:'This is my demo Facebook application (JS SDK)!',
                caption:'Caption of the Post',
                description:'It is fun to write Facebook App!',
                actions:{ name:'Start Learning', link:'http://www.takwing.idv.hk/tech/fb_dev/index.php'},
                message:'is learning how to develop Facebook apps.'
            };
            FB.api('/me/feed', 'post', publish, function (response) {
                alert(JSON.stringify(response));
                console.log('A post had just been published into the stream on your wall. ' + JSON.stringify(response));
            });
        } else {
            alert('User cancelled login or did not fully authorize.');
        }
    }, {scope:'user_likes,offline_access,publish_stream'});
    return false;
}