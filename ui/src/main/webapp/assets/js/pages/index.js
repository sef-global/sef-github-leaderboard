let tblScoreHistory = null;

$(function () {
    loadNavAndFooter('assets/content/static');  //relative path to content directory

    const boardId = getUrlParameter("board");

    if (boardId === undefined) {
        $("#main-header").html($("#template-404").html());
    } else {
        loadMainHeader(boardId);

    }
});

function loadMainHeader(boardId) {
    const url = window.location.origin + "/invoker/open/api/giraffe/v1/boards/" + boardId;
    const template = $("#template-main-header");
    const target = $("#main-header");
    renderData(template, url, target, false, null,(data, textStatus, jqXHR) => {
        loadLeaderBoard(boardId);
    });

}

function loadLeaderBoard(boardId) {
    const url = window.location.origin + `/invoker/open/api/giraffe/v1/boards/${boardId}/scores`;
    const table = $('#example').DataTable({
        "processing": true,
        "serverSide": true,
        "bFilter": false,
        "ordering": false,
        "oLanguage": {
            "oPaginate": {
                "sPrevious": '<i class="ni ni-bold-left"></i>',
                "sNext": '<i class="ni ni-bold-right"></i>'
            }
        },
        ajax: {
            "url": url,
            data: function (params) {
                return {
                    "limit": params.length,
                    "offset": params.start
                };
            },
            "type": "GET",
            dataFilter: function (data) {
                data = JSON.parse(data);
                data.recordsTotal = data.count;
                data.recordsFiltered = data.count;
                return JSON.stringify(data);
            }
        },
        "columns": [
            {
                "mData": "name",
                "mRender": function (data, type, row) {
                    return '<span>' +
                        '<img src="'+row.image+'" alt="Circle image" class="img-fluid rounded-circle profile-image" >' +
                            // row.name +
                        '</span>';
                }
            },
            {"data": "name"},
            {"data": "rank"},
            {"data": "points"}
        ]
    });

    $('#example tbody').on('click', 'tr', function () {
        let data = table.row( this ).data();
        $("#modal-score-history").modal('show');
        $('#modal-score-history-title').text(data.name);
        loadScoreHistory(data.id);
    } );
}

function loadScoreHistory(entityId) {
    const url = window.location.origin + `/invoker/open/api/giraffe/v1/entities/${entityId}/scores`;
    if(tblScoreHistory != null){
        tblScoreHistory.destroy();
    }
    tblScoreHistory = $('#tbl-score-history').DataTable({
        "processing": true,
        "serverSide": true,
        "bFilter": false,
        "ordering": false,
        "responsive": true,
        "oLanguage": {
            "oPaginate": {
                "sPrevious": '<i class="ni ni-bold-left"></i>',
                "sNext": '<i class="ni ni-bold-right"></i>'
            }
        },
        ajax: {
            "url": url,
            data: function (params) {
                return {
                    "limit": params.length,
                    "offset": params.start
                };
            },
            "type": "GET",
            dataFilter: function (data) {
                data = JSON.parse(data);
                data.recordsTotal = data.count;
                data.recordsFiltered = data.count;
                return JSON.stringify(data);
            }
        },
        "columns": [
            {
                "mData": "createdAt",
                "mRender": function (data, type, row) {
                    return moment.unix(data).format("YYYY-MM-DD HH:mm");
                }
            },
            {"data": "points"},
            {
                "mData": "description",
                "mRender": function (data, type, row) {
                    return convertPlainTextToHyperLinks(data);
                }
            }
        ]
    });
    
}
