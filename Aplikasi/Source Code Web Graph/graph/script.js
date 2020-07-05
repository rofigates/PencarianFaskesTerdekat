function sender(url, type, data, dataType, successFunction, errorFunction){
    $.ajax({
        url: url,
        type: type,
        data: data,
        dataType: dataType,
        success: successFunction,
        errors: errorFunction
    });
}