$(document).ready(function() {
    dataSend();
    $("button[value='btnSearch']").click(dataSend());
})

function dataSend() {
    let boardSearch={
        types:$("#types").val(),
        search:$("#search").val()
    };
    $.ajax({
        url: "/board",
        data: boardSearch,
        type:"POST",
    }).done(function (fragment) {
        $("#boardList").replaceWith(fragment);
        alert("hello!");
    });
}