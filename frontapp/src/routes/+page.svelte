<script>
    import {onMount} from "svelte";
    import { loginUser } from '../stores';

    const images = [
        { alt: 'erbology', src: 'https://flowbite.s3.amazonaws.com/docs/gallery/square/image.jpg' },
        { alt: 'shoes', src: 'https://flowbite.s3.amazonaws.com/docs/gallery/square/image-1.jpg' },
        { alt: 'small bag', src: 'https://flowbite.s3.amazonaws.com/docs/gallery/square/image-2.jpg' },
        { alt: 'plants', src: 'https://flowbite.s3.amazonaws.com/docs/gallery/square/image-3.jpg' },
        { alt: 'watch', src: 'https://flowbite.s3.amazonaws.com/docs/gallery/square/image-4.jpg' },
        { alt: 'shoe', src: 'https://flowbite.s3.amazonaws.com/docs/gallery/square/image-5.jpg' },
        { alt: 'cream', src: 'https://flowbite.s3.amazonaws.com/docs/gallery/square/image-6.jpg' },
        { alt: 'small bag', src: 'https://flowbite.s3.amazonaws.com/docs/gallery/square/image-7.jpg' },
        { alt: 'lamp', src: 'https://flowbite.s3.amazonaws.com/docs/gallery/square/image-8.jpg' },
        { alt: 'toiletbag', src: 'https://flowbite.s3.amazonaws.com/docs/gallery/square/image-9.jpg' },
        { alt: 'playstation', src: 'https://flowbite.s3.amazonaws.com/docs/gallery/square/image-10.jpg' },
        { alt: 'bag', src: 'https://flowbite.s3.amazonaws.com/docs/gallery/square/image-11.jpg' }
    ];
    import { Navbar, NavBrand, NavLi, NavUl, NavHamburger, Button, Gallery, Modal, Label, Input, Checkbox } from 'flowbite-svelte';

    onMount(async () => {
        try {
            const response = await fetch('http://localhost:8080/api/v1/member/loginUser', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include'
            });
            console.log(response)
            if (response.ok) {
                const data = await response.json();
                let username = data.data.member.username
                // alert('회원정보가 확인되었습니다.:')
                loginUser.set(data.data.member); // 스토어 업데이트
                console.log(username)
            } else {
                console.error('서버 응답 오류:', response.statusText);
            }
        } catch (error) {
            console.log('에러렁')
            console.error('오류 발생:', error);
        }
    });

    onMount(async () => {
        try {
            const response = await fetch('http://localhost:8080/api/v1/member/refresh', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include'
            });
            if (response.ok) {
                const data = await response.json();
                loginUser.set(data.data.member); // 스토어 업데이트
            } else {
                window.location.href = '/login';
                alert("장기 미접속 시 로그아웃 시켰습니다. 다시 로그인을 진행해주세요.")
                console.error('서버 응답 오류:', response.statusText);
            }
        } catch (error) {
            console.log('토큰에러렁')
            console.error('오류 발생:', error);
        }
    });

    const logout = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/v1/member/logout`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include'
            });
            console.log(response)
            if (response) {
                const data = await response.json()
                if (data.resultCode === 'S-3') {
                    window.location.href = '/login';
                    alert('로그아웃 되었습니다.')
                } else {
                    const errorMessage = data.errorMessage;
                    console.error('로그아웃 실패:', errorMessage)
                }
            }
        } catch (error) {
            console.error('오류 발생:', error);
            alert('다시 시도 해주세요2.');
        }
    };
</script>

<Navbar let:NavContainer color="primary">
    <NavContainer class="border px-5 py-2 rounded-lg bg-gray-200">
        <NavBrand href="/">
            <div class="me-3 h-6 sm:h-9" alt="Flowbite Logo" />
            <span class="self-center whitespace-nowrap text-xl font-semibold">Flowbite</span>
        </NavBrand>
        <NavHamburger />
        <NavUl>
            <NavLi href="/">Home</NavLi>
            <NavLi href="/about">About</NavLi>
            <NavLi href="/docs/components/navbar">Navbar</NavLi>
            <NavLi href="/pricing">Pricing</NavLi>
            <NavLi href="/contact">Contact</NavLi>
            <Button on:click={() => {logout();}}  color='light'>로그아웃</Button>
        </NavUl>
    </NavContainer>
</Navbar>

<div class="flex items-center justify-center py-4 md:py-8 flex-wrap gap-3 mb-3 mx-autoß">
    <Button pill size="xl" outline>All categories</Button>
    <Button pill size="xl" color="alternative">Shoes</Button>
    <Button pill size="xl" color="alternative">Bags</Button>
    <Button pill size="xl" color="alternative">Electronics</Button>
    <Button pill size="xl" color="alternative">Gaming</Button>
</div>

<Gallery items={images} class="gap-4 grid-cols-2 md:grid-cols-3" />

