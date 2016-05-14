/* [√‚√≥]  http://cafe.daum.net/_c21_/bbs_search_read?grpid=1DpW4&fldid=II11&datanum=27
        http://mimul.com/examples/ria/cart.html */

$(document).ready(
	function()
	{
		$('#products a')
			.bind(
				'click',
				function() {
					$(this.parentNode)
						.TransferTo(
							{
								to:addProductToCart(this.parentNode),
								className:'transferProduct',
								duration: 400
							}
						);
					return false;
				}
			);
		$('article.product').Draggable({revert: true, fx: 300, ghosting: true, opacity: 0.4});
		$('#cart').Droppable(
			{
				accept :		'product', 
				activeclass:	'activeCart', 
				hoverclass:		'hoverCart',
				tolerance:		'intersect',
				onActivate:		function(dragged)
				{
					if (!this.shakedFirstTime) {
						$(this).Shake(3);
						this.shakedFirstTime = true;
					}
				},
				onDrop:			addProductToCart
			}
		);
	}
);
var addProductToCart = function(dragged)
{
	var cartItem;
	var productName = $('h3', dragged).html();
	var productId = $(dragged).attr('id');
	var isInCart = $('#' + productId + '_cart');
	if (isInCart.size() == 1) {
		var quantity = parseInt(isInCart.find('span.quantity').html()) + 1;
		isInCart.find('span.quantity').html(quantity+'').end().Pulsate(300, 2);
		cartItem = isInCart.get(0);
	} else {
		$('#zzim')
			.append('<div class="productCart" id="' + productId + '_cart">' + productName + '<br/>'+'<a href="#">remove</a>'+'<br/></div>')
			.find('div.productCart:last')
			.fadeIn(400)
			.find('a')
			.bind(
				'click', 
				function(){
					$(this.parentNode).DropOutDown(
						400,
						function() {
							$(this).remove();
						
						}
					);
					return false;
				}
			);
		cartItem = $('div.productCart:last').get(0);
	}
	
	return cartItem;
};

