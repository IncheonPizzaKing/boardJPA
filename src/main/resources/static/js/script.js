        const types = document.querySelector("#types");
        const search = document.querySelector("#search");
        const address = document.querySelector("#address");
        const size = document.querySelector("#size");

        function dat(data) {
            alert(data);
        }

        function dataSend() {
            const viewList = document.querySelector("#viewList");
            let dataSearch = {
                types: types.value,
                search: search.value,
                size: size.value
            };
            console.log(dataSearch.types, dataSearch.search);
            $.ajax({
                url: "/" + address.value,
                data: dataSearch,
                type: "POST",
                success: function (data) {
                    console.log(data);
                    $(viewList).replaceWith(data);
                }
            });
        }

        function paging(now) {
            const viewList = document.querySelector("#viewList");
            let dataSearch = {
                page: now,
                types: types.value,
                search: search.value,
                size: size.value
            };
            console.log(now, dataSearch.types, dataSearch.search);
            $.ajax({
                url: "/" + address.value + "/",
                data: dataSearch,
                type: "POST",
                success: function (data) {
                    console.log(data);
                    $(viewList).replaceWith(data);
                }
            });
        }

        function remove() {
            const viewList = document.querySelector("#viewList");
            const query = 'input[name="selected"]:checked';
            const selectedAll = document.querySelectorAll(query);
            let selectedValues = new Array();
            for (i = 0; i < selectedAll.length; i++) {
                selectedValues[i] = selectedAll[i].value;
                console.log(selectedAll[i].value);
            }
            console.log(selectedValues);
            $.ajax({
                url: "/" + address.value + "/delete",
                data: {
                    sList: selectedValues
                },
                type: "POST",
                success: function (data) {
                    console.log(data);
                    dataSend(address);
                }
            });
        }

