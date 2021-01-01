let tblScoreHistory = null;
let timeRange;
let table = null;

$(function () {
    loadNavAndFooter('assets/content/static');  //relative path to content directory
    loadData('THIS_MONTH');
});

function loadLeaderBoard() {
    const url = window.location.origin + `/scores`;
    if(table != null){
        table.destroy();
    }
    table = $('#tbl-leaderboard').DataTable({
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
                    "offset": params.start,
                    ...timeRange
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
                "mData": "username",
                "mRender": function (data, type, row) {
                    return '<span>' +
                        '<img src="' + row.image + '&s=100" alt="Circle image" class="img-fluid rounded-circle profile-image" >' +
                        // row.name +
                        '</span>';
                }
            },
            {"data": "username"},
            // {
            //     "mData": "rank",
            //     "mRender": function (data, type, row) {
            //         return '<div class="font-source-code text-right">' +
            //                     data +
            //                 '</div>';
            //     }
            // }
            {
                "mData": "points",
                "mRender": function (data, type, row) {
                    return '<div class="font-source-code text-right">' +
                                data +
                            '</div>';
                }
            }
        ]
    });

    $('#tbl-leaderboard tbody').on('click', 'tr', function () {
        let data = table.row(this).data();
        $("#modal-score-history").modal('show');
        $('#modal-score-history-username').text(data.username);
        $('#modal-score-history-avatar').attr("src", data.image);
        $('#modal-score-history-url').attr("href", data.url);
        loadScoreHistory(data.id);
    });
}

function loadScoreHistory(entityId) {
    const url = window.location.origin + `/users/${entityId}/scores`;
    if (tblScoreHistory != null) {
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
                    "offset": params.start,
                    ...timeRange
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
                    return '<span class="font-source-code">' + moment.unix(data).format("YYYY-MM-DD HH:mm") + '</span>';
                }
            },
            {
                "mData": "prUrl",
                "mRender": function (data, type, row) {
                    return '<a target="_blank" href="' + data + '">' + data + '</a>';
                }
            }
        ]
    });

}

function loadData(range) {
    switch (range) {
        case 'THIS_MONTH':
            timeRange = {
                from : moment().startOf('month').format('X'),
                to : moment().endOf('month').format('X')
            };
            break;
        case 'LAST_MONTH':
            timeRange = {
                from : moment().subtract(1,'months').startOf('month').format('X'),
                to : moment().subtract(1,'months').endOf('month').format('X')
            };
            break;
        case 'ALL_TIME':
            timeRange = {};
    }

    loadLeaderBoard();
}
