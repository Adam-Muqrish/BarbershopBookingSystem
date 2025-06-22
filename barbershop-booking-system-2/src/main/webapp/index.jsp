<%@ include file="/WEB-INF/views/includes/header.jsp"%>
<body class="flex flex-col min-h-screen justify-between bg-yellow-100">
	<%@ include file="/WEB-INF/views/includes/nav.jsp"%>

	<main class="flex flex-col justify-between bg-white">
		<!-- Hero Banner -->
		<section
			class="relative h-[60vh] bg-cover bg-center flex items-center justify-center text-white"
			style="background-image: url(https://images.squarespace-cdn.com/content/v1/5d52feb1aa6f990001929012/e89c181b-dd09-420e-8e80-06aa47a5a37a/todecacho.png?format=1500w);">
			<div class="bg-black/40 absolute inset-0"></div>
			<h1 class="text-4xl lg:text-6xl font-extrabold z-10">HUGI
				BARBERSHOP</h1>
		</section>

		<section id=aboutUs
			class="relative bg-white px-6 lg:px-24 pt-16 pb-10 mb-2 2xl:mb-0 2xl:px-36 z-20 md:overflow-visible">
			<h2
				class="text-red-600 uppercase text-sm font-bold mb-2 2xl:text-4xl">About
				Us</h2>

			<div
				class="grid grid-cols-1 md:grid-cols-2 gap-10 items-center md:relative md:z-20">
				<!-- Text Content -->
				<div>
					<h2 class="text-xl lg:text-4xl 2xl:text-3xl font-extrabold mb-4">Welcome
						to Hugi Barbershop</h2>
					<p class="text-gray-700 mb-4">At Hugi Barbershop, we combine
						the classic art of barbering with contemporary techniques. Our
						team of skilled barbers specializes in modern fades, sharp beard
						trims, and clean, stylish precision haircuts.</p>
					<p class="text-gray-700">Whether you’re here for a quick
						touch-up or a full grooming session, we promise top-tier care and
						unmatched attention and expertise.</p>
					<p class="mt-4 font-medium text-gray-800">Ready for your next
						cut? Book your appointment today — we are looking forward to
						serving you!</p>
				</div>

				<!-- Image Overlay -->
				<div class="relative h-[400px] 2xl:h-[600px] md:overflow-visible">
					<!-- Image that appears lower visually but overlaps (Barber 1) -->
					<img
						src="https://images.squarespace-cdn.com/content/v1/5d52feb1aa6f990001929012/e89c181b-dd09-420e-8e80-06aa47a5a37a/todecacho.png?format=1500w"
						alt="Barber 1"
						class="absolute bottom-12 md:-top-12 md:left-0 xl:left-30 w-64 md:w-52 lg:w-56 md:h-[28rem] rounded-lg shadow-md z-30 md:object-cover transition-transform duration-300 group-hover:scale-105 group-hover:-translate-y-1 group-hover:-translate-x-1 border border-gray-500 border-5" />

					<!-- Image that appears higher visually but underneath (Barber 2) -->
					<img
						src="https://images.squarespace-cdn.com/content/v1/5d52feb1aa6f990001929012/e89c181b-dd09-420e-8e80-06aa47a5a37a/todecacho.png?format=1500w"
						alt="Barber 2"
						class="absolute right-0 top-12 md:-top-40 xl:-top-50 md:right-0 xl:right-30 w-64 md:w-52 lg:w-56 md:h-[28rem] rounded-lg shadow-lg z-10 md:object-cover transition-transform duration-300 group-hover:scale-105 group-hover:translate-y-1 group-hover:translate-x-1 border border-gray-500 border-5" />
				</div>

			</div>
		</section>

		<!-- Appointment CTA Section -->
		<section
			class="md:relative bg-gray-100 p-8 rounded-lg shadow mx-6 lg:mx-24 md:my-36 2xl:my-36 overflow-visible">
			<div
				class="grid grid-cols-1 md:grid-cols-2 items-center relative z-10 md:gap-10 lg:gap-0 overflow-visible">

				<!-- Image Container -->
				<div
					class="relative order-1 md:order-1 overflow-visible flex justify-center">
					<div
						class="w-96 md:w-52 lg:w-56 xl:w-96 md:h-[28rem] md:absolute md:left-0 md:-top-60 2xl:left-20">
						<img
							src="https://images.squarespace-cdn.com/content/v1/5d52feb1aa6f990001929012/e89c181b-dd09-420e-8e80-06aa47a5a37a/todecacho.png?format=1500w"
							alt="Haircut Action"
							class="w-96 md:w-52 lg:w-56 xl:w-96 md:h-[28rem] object-cover rounded-lg shadow-lg border border-gray-500 border-5" />
					</div>
				</div>

				<!-- Text -->
				<div class="order-2 md:order-2 mt-6 md:mt-0">
					<h2 class="text-2xl md:text-3xl font-bold mb-4">Let’s Make An
						Appointment!</h2>
					<p class="text-gray-700 mb-4">Book your haircut online today
						for a stress-free experience—skip the wait, choose your preferred
						barber, get instant confirmation, and enjoy hassle-free reminders.
						Your perfect haircut is just a click away!</p>
					<a href="#booking"
						class="inline-block bg-red-600 hover:bg-red-700 text-white font-semibold py-3 px-6 rounded-md transition duration-300 ease-in-out">
						BOOK NOW ! </a>
				</div>

			</div>
		</section>

	</main>

	<%@ include file="/WEB-INF/views/includes/footer.jsp"%>

</body>
</html>