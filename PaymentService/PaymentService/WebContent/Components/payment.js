const PORT = 8081;

function getData() {
	//For Retrieve Payment Data
  $.ajax({
    url: `http://localhost:${PORT}/PaymentService/api/v2/payment/getpayments`,
    type: "GET",
    success: function (result) {
      const paymentData = result;
      const tableId = $("#payment_table");
      let output = ``;
      paymentData.forEach((data) => {
        output =
          output +
          `<tr>
          <td>${data.id}</td>
          <td>${data.recipientId}</td>
          <td>${data.total}</td>
          <td>${data.paymentMethod}</td>
          <td>${data.researcheId}</td>
          <td>${data.fundingbodyId}</td>
          <td>${data.buyerId}</td>
          <td style="display: flex; flex-direction: row; align-itmes: center">
            <button
              id="${data.id}"
              class="btn btn-danger btnRemove"
              type="button"
              style="margin-left: 10px"
            >
              Delete
            </button>
          </td>
        </tr>
        `;
      });

      tableId.html(output);
    },
  });
}

getData();

//For Delete Payment Data
$(document).on("click", ".btnRemove", (event) => {
  const deleteId = event.target.id;
  const target = event.target;
  target.innerHTML = `<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Wait`;

  $.ajax({
    url: `http://localhost:${PORT}/PaymentService/api/v2/payment/deletebyid/${deleteId}`,
    type: "DELETE",
    complete: function (result) {
      if (result.status == 200) {
        target.parentNode.parentNode.style.display = "none";
        $("#deleteLabel").css("display", "block");

        setTimeout(() => {
          $("#deleteLabel").css("display", "none");
        }, 200000);
      } else {
        $("#deleteLabelerror").css("display", "block");
        target.innerHTML = `Delete`;
        setTimeout(() => {
          $("#deleteLabelerror").css("display", "none");
        }, 200000);
      }
    },
  });
});

////For Insert Payment Data
$(document).on("click", ".submit", (event) => {
  event.preventDefault();
  const target = event.target;
  const recipientid = Number.parseInt($("#recipientid").val());
  const totamount = Number.parseFloat($("#totamount").val());
  const paymentmethod = $("#paymentmethod").val();
  const researcherid = Number.parseInt($("#researcherid").val());
  const fbodyid = Number.parseInt($("#fbodyid").val());
  const buyerid = Number.parseInt($("#buyerid").val());

  target.innerHTML = `<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Wait`;

  $.ajax({
    url: `http://localhost:${PORT}/PaymentService/api/v2/payment/addpayment`,
    type: "POST",
    data: JSON.stringify({
      recipient_id: recipientid,
      total: totamount,
      paymentMethod: paymentmethod,
      researcher_id: researcherid,
      fundingbody_id: fbodyid,
      buyerId: buyerid,
    }),
    contentType: "application/json",
    dataType: "json",
    complete: function (response, status) {
      const newlyAddedItems = {
        recipientid: recipientid,
        totamount: totamount,
        paymentmethod: paymentmethod,
        researcherid: researcherid,
        fbodyid: fbodyid,
        buyerid: buyerid,
      };
      onItemSaveComplete(response, status, newlyAddedItems, target);
    },
  });
});

function onItemSaveComplete(response, status, newlyAddedItems, target) {
  if (response.status == 201) {
    $("#insertLabel").css("display", "block");
    target.innerHTML = `Submit`;
    let tableId = $("#payment_table");
    let output = `${tableId.html()}`;
    output =
      output +
      `<tr>
      <td>${Number.parseInt(Math.random() * 100)}</td>
      <td>${newlyAddedItems.recipientid}</td>
      <td>${newlyAddedItems.totamount}</td>
      <td>${newlyAddedItems.paymentmethod}</td>
      <td>${newlyAddedItems.researcherid}</td>
      <td>${newlyAddedItems.fbodyid}</td>
      <td>${newlyAddedItems.buyerid}</td>
      <td style="display: flex; flex-direction: row; align-itmes: center">
        <button
          class="btn btn-danger btnRemove"
          type="button"
          style="margin-left: 10px"
        >
          Delete
        </button>
      </td>
    </tr>
    `;
    tableId.html(output);
    setTimeout(() => {
      $("#insertLabel").css("display", "none");
    }, 2000);
  } else {
    $("#insertLabelerror").css("display", "block");
    target.innerHTML = `Submit`;
    setTimeout(() => {
      $("#insertLabelerror").css("display", "none");
    }, 2000);
  }
}


