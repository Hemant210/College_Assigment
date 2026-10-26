/**
 * Philips Airfryer PDP Interactive Controller
 */

document.addEventListener('DOMContentLoaded', () => {
  // 1. Gallery Thumbnail Switcher
  const mainImg = document.getElementById('mainProductImg');
  const thumbBtns = document.querySelectorAll('.thumb-btn');

  thumbBtns.forEach(btn => {
    btn.addEventListener('click', () => {
      const newSrc = btn.getAttribute('data-img');
      if (newSrc && mainImg) {
        // Quick subtle fade transition
        mainImg.style.opacity = '0.6';
        mainImg.src = newSrc;
        setTimeout(() => {
          mainImg.style.opacity = '1';
        }, 120);

        thumbBtns.forEach(b => {
          b.classList.remove('active');
          b.setAttribute('aria-selected', 'false');
        });
        btn.classList.add('active');
        btn.setAttribute('aria-selected', 'true');
      }
    });
  });

  // 2. Interactive EMI Modal
  const emiModal = document.getElementById('emiModalDialog');
  const openEmiBtn = document.getElementById('openEmiBtn');
  const closeEmiBtn = document.getElementById('closeEmiModal');

  if (openEmiBtn && emiModal) {
    openEmiBtn.addEventListener('click', () => {
      emiModal.showModal();
    });
  }

  if (closeEmiBtn && emiModal) {
    closeEmiBtn.addEventListener('click', () => {
      emiModal.close();
    });
  }

  // Close modal when clicking backdrop
  if (emiModal) {
    emiModal.addEventListener('click', (e) => {
      const rect = emiModal.getBoundingClientRect();
      const isInDialog = (
        rect.top <= e.clientY &&
        e.clientY <= rect.top + rect.height &&
        rect.left <= e.clientX &&
        e.clientX <= rect.left + rect.width
      );
      if (!isInDialog) {
        emiModal.close();
      }
    });
  }

  // 3. Special Offers: Coupon Code Handler
  const couponBtn = document.getElementById('couponBtn');
  if (couponBtn) {
    couponBtn.addEventListener('click', () => {
      const code = couponBtn.getAttribute('data-code') || 'AIRFRYERMANIA';
      navigator.clipboard?.writeText(code).catch(() => {});
      couponBtn.textContent = 'APPLIED';
      couponBtn.style.backgroundColor = '#16a34a';
      couponBtn.style.borderColor = '#16a34a';
      couponBtn.style.color = '#ffffff';
      showToast(`Coupon '${code}' applied! ₹100 discount added.`);
    });
  }

  // 4. Quantity Selector
  const qtyVal = document.getElementById('qtyVal');
  const qtyMinus = document.getElementById('qtyMinus');
  const qtyPlus = document.getElementById('qtyPlus');
  let currentQty = 1;

  if (qtyMinus && qtyPlus && qtyVal) {
    qtyMinus.addEventListener('click', () => {
      if (currentQty > 1) {
        currentQty--;
        qtyVal.textContent = currentQty;
      }
    });

    qtyPlus.addEventListener('click', () => {
      if (currentQty < 10) {
        currentQty++;
        qtyVal.textContent = currentQty;
      }
    });
  }

  // 5. Add to Cart & Buy Now Actions
  const addToCartBtn = document.getElementById('addToCartBtn');
  const buyNowBtn = document.getElementById('buyNowBtn');
  const cartBadge = document.getElementById('cartBadge');
  let totalCartItems = 0;

  if (addToCartBtn && cartBadge) {
    addToCartBtn.addEventListener('click', () => {
      totalCartItems += currentQty;
      cartBadge.textContent = totalCartItems;
      cartBadge.style.transform = 'scale(1.4)';
      setTimeout(() => {
        cartBadge.style.transform = 'scale(1)';
      }, 200);
      showToast(`Added ${currentQty} Philips Airfryer to cart!`);
    });
  }

  if (buyNowBtn) {
    buyNowBtn.addEventListener('click', () => {
      showToast('Proceeding to checkout with Philips Airfryer...');
    });
  }

  // 6. Delivery Pincode Checker
  const checkPinBtn = document.getElementById('checkPinBtn');
  const pincodeInput = document.getElementById('pincodeInput');
  const deliveryMsg = document.getElementById('deliveryMsg');

  if (checkPinBtn && pincodeInput && deliveryMsg) {
    checkPinBtn.addEventListener('click', () => {
      const code = pincodeInput.value.trim();
      if (/^\d{6}$/.test(code)) {
        deliveryMsg.className = 'delivery-msg success';
        deliveryMsg.textContent = `✓ Delivery available to ${code} by tomorrow (Free Express Delivery)`;
      } else {
        deliveryMsg.className = 'delivery-msg error';
        deliveryMsg.textContent = 'Please enter a valid 6-digit postal pincode.';
      }
    });
  }

  // 7. Dynamic Search Placeholder Typewriter
  const searchInput = document.getElementById('searchInput');
  const searchTerms = ['Search For Iron', 'Search For Airfryer', 'Search For Garment Steamer', 'Search For Mixer Grinder'];
  let termIdx = 0;
  let charIdx = 0;
  let isDeleting = false;

  function typeEffect() {
    if (!searchInput || document.activeElement === searchInput) return;
    const currentTerm = searchTerms[termIdx];

    if (!isDeleting) {
      charIdx++;
      searchInput.setAttribute('placeholder', currentTerm.substring(0, charIdx));
      if (charIdx === currentTerm.length) {
        isDeleting = true;
        setTimeout(typeEffect, 2000);
        return;
      }
    } else {
      charIdx--;
      searchInput.setAttribute('placeholder', currentTerm.substring(0, charIdx));
      if (charIdx === 0) {
        isDeleting = false;
        termIdx = (termIdx + 1) % searchTerms.length;
      }
    }
    setTimeout(typeEffect, isDeleting ? 40 : 80);
  }

  setTimeout(typeEffect, 1500);

  // Helper: Toast Notifications
  const toast = document.getElementById('toastNotice');
  let toastTimer;
  function showToast(message) {
    if (!toast) return;
    clearTimeout(toastTimer);
    toast.textContent = message;
    toast.classList.add('show');
    toastTimer = setTimeout(() => {
      toast.classList.remove('show');
    }, 3000);
  }
});
